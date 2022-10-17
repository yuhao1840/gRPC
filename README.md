# in Windows PowerShell
>cd C:\git\hyu\gRPC\src\main\proto

# compile JS file from proto file
>../../../target/protoc-plugins/protoc-3.4.0-windows-x86_64.exe --js_out=./ person.proto

# compile python file from proto file
>../../../target/protoc-plugins/protoc-3.4.0-windows-x86_64.exe --python_out=./ person.proto
