# users.sh
# Simple shell script to generate five users
# usage: sh users.sh [token]
#!/bin/bash
fnames=(keneth kara simonne eduardo frances)
lnames=(flowers ludlum garcia ogren svedberg)

for n in {0..4}
do
    curl --header "Content-type: application/json" --header "Authorization: token=$1" -X POST "http://127.0.0.1:5000/user" -d '{
        "email": "'${fnames[$n]}'@gmail.com", 
        "password": "123'$n'", 
        "fname": "'${fnames[$n]}'", 
        "lname": "'${lnames[$n]}'"}'
done
