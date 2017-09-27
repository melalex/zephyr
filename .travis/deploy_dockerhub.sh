#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" == "develop" ]; then
    ./gradlew dockerPushImage -PdockerUserName="$DOCKER_LOGIN" -PdockerPassword="$DOCKER_PASSWORD" -PtagVersion="latest";
fi