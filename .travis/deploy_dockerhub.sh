#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" == "master" ]; then
    ./gradlew dockerPushImage -Pdocker.tag="latest";
fi