#!/bin/bash

#exit if there is an error
set -e

#if [-z "$1"]
#then
#    echo "usage $0 eb_env_name"
#    exit 1
#fi

eb_env=$1
echo ${eb_env}

rm -rf .elasticbeanstalk

#init eb for the project
eb init trading-app --platform java --region us-east-1
echo "at 1"
eb use ${eb_env}

#Edit eb config file
cat >> .elasticbeanstalk/config.yml <<_EOF
deploy:
  artifact: target/trading-1.0-SNAPSHOT-elastic-beanstalk.zip
_EOF

#deploy
echo "deploy"
eb deploy

exit 0