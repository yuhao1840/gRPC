# Recommended Eclipse Plug-In: Protocol Buffer Editor

# How to compile proto file into multiple programming languages

## go to proto source files folder in Windows PowerShell
>cd %PROJECT_ROOT%\src\main\proto

## compile a proto file to JS
>../../../target/protoc-plugins/protoc-3.4.0-windows-x86_64.exe --js_out=./ person.proto

## compile a proto file to Python
>../../../target/protoc-plugins/protoc-3.4.0-windows-x86_64.exe --python_out=./ person.proto

# src/main/node-client is a NodeJS-based client to gRPC Bank Service

# setup Nginx using docker-compose
https://dev.to/aminnairi/quick-web-server-with-nginx-on-docker-compose-43ol
$ cd nginx
$ docker-compose up --detach
$ docker-compose stop
