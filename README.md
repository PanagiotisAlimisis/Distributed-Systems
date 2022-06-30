# Distributed-Systems

## Project local setup

```
git clone git@github.com:PanagiotisAlimisis/Distributed-Systems.git
cd Distributed-Systems
mysql -u $USER -p < SQL/create_and_insert_data.sql
cd Backend
chmod u+x start_server.sh
```
Also you need a ```.env``` file identical to ```.env.example``` but with your own values.
```
./start_server.sh
```

## Public Deployment
You need
- A vm with Jenkins installed and publicly exposed, so that you can access it from your browser.
- A vm for the deployment with ansible.
- A vm for the deployment with ansible+docker.
- A vm for the deployment with ansible+kubernetes.

1. Add to your ssh config ```~/.ssh/config``` file on your local machine a record like this: 
    ```
    Host jenkins-server
        HostName <<public-ip>>
        User <<remote-user>>
        IdentityFile <<ssh_public_key>> 
    ```
2. Also add to the jenkins server at your jenkins user the following lines:
   ```
   Host docker-vm
        HostName <<public-ip>>
        User <<remote-user>>
        IdentityFile <<ssh_public_key>> 
   Host test-vm
        HostName <<public-ip>>
        User <<remote-user>>
        IdentityFile <<ssh_public_key>> 
   Host kubernetes-vm
        HostName <<public-ip>>
        User <<remote-user>>
        IdentityFile <<ssh_public_key>> 
   ```
3. Create a Jenkins Pipeline and add a webhook in your git project, so that jenkins will be triggered automatically after every commit on your main branch.
4. Create a file ```db-password.txt``` in your jenkins vm, and put it under ```$HOME``` for the jenkins user.
   1. The file should contain only one line with your password as plain text. (Assuming that only you have access in the jenkins vm)
5. Add your email to Jenkins server as ```email```. 
6. Create a free account at [DockerHub](https://hub.docker.com).
7. Go to file ```Jenkinsfile``` and change the values ```panagiotishua```, to your username from DockerHub.
8. Add this file under ```/usr/lib/systemd/system/``` on your ```ansible-vm``` with name ```springapp.service```
```
[Unit]
Description=App service systemd

[Service]
User=panagiotis
Environment="DB_USER="
Environment="DB_PASSWORD="
Environment="MAILHOG_HOST="
Environment="DB_PORT="
Environment="DB_HOST="
ExecStart=/usr/bin/java -jar /home/$USER/FreeTransportation-0.0.1-SNAPSHOT.jar

[Install]
WantedBy=multi-user.target
```
8. Add this file under ```/home/$USER``` on your ```kubernetes-vm``` with name ```config-map.yaml```
```
apiVersion: v1
kind: ConfigMap
metadata:
  name: secrets
data:
  DB_HOST: 
  DB_PORT: 
  DB_USER: root
  DB_PASSWORD: 
  MAILHOG_HOST: 
```
9. Change ```auth.file``` to your desired values. ```username:password```. To generate a password you need to run mailhog container, connect to it and run ```Mailhog bcrypt <<your_password>>```. Copy this value to ```auth.file```.
10. SSH to your kubernetes vm and copy the ```~/.kube/config``` locally to ```~/.kube/config``` and delete certificates and add this line under ```-cluster```
```insecure-skip-tls-verify: true``` 
11. For the kubernetes vm you need to have configured a dns and certification files. Go to [ClouDNS](https://www.cloudns.net/main/) and create 2 dns A entries. One for backend and one for mailhog.
12. Then go to [ZeroSSL](https://manage.sslforfree.com/certificates) and create 2 certificates by following the instructions, one for every dns.
13. Create locally a directory ```certs``` -> ```certs/app``` -> ```certs/mailhog``` and download there the files from ZeroSSL.
14. Then navigate to ```certs/app``` and run
```
kubectl create secret generic tls-secret --from-file=tls.crt=certificate.crt --from-file=tls.key=private.key --from-file=ca.crt=ca_bundle.crt
```
15. Then navigate to ```certs/mailhog``` and run
```
kubectl create secret generic tls-secret-mailhog --from-file=tls.crt=certificate.crt --from-file=tls.key=private.key --from-file=ca.crt=ca_bundle.crt
```
16. Go to each file under ```playbooks``` and change the var ```user``` and var ```dockerhub_username``` from ```deploy-to-docker-vm.yaml```.
17. Finally, make a new commit, and login to your jenkins instance from your browser to see your build running.
