aws dynamodb create-table --table-name TeamMember --attribute-definitions AttributeName=ID,AttributeType=S AttributeName=Name,AttributeType=S  --key-schema AttributeName=ID,KeyType=HASH AttributeName=Name,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5


aws dynamodb put-item --table-name TeamMember --item file://member1.json --return-consumed-capacity TOTAL
aws dynamodb put-item --table-name TeamMember --item file://member2.json --return-consumed-capacity TOTAL
aws dynamodb put-item --table-name TeamMember --item file://member3.json --return-consumed-capacity TOTAL
aws dynamodb put-item --table-name TeamMember --item file://member4.json --return-consumed-capacity TOTAL

