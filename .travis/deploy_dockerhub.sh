#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" == "master" ]; then
    ./gradlew pushImage -Pdocker.tag="latest";
fi