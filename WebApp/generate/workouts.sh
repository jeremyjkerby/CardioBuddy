# workouts.sh
# Simple shell script to generate 25 workouts
# usage: sh workouts.sh [token]
#!/bin/bash
names=(keneth kara simonne eduardo frances)

for i in {0..4}
do
    for n in {0..4}
    do
        curl --header "Content-type: application/json" --header "Authorization: token=$1" -X POST "http://127.0.0.1:5000/workouts" -d '[
        {
            "u_id": "'$i'",
            "date": "1112223334",
            "type": "3", 
            "duration": "30", 
            "calories": "30", 
            "distance": "2.54",
            "notes": "'${names[i]}' workout."}]'
    done
done
