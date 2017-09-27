#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" == "master" ]; then
    wget -qO- https://toolbelt.heroku.com/install-ubuntu.sh | sh;
    heroku plugins:install heroku-container-registry;
    ./gradlew ping:distDocker -PtagVersion="release";

    docker login -u "$HEROKU_EMAIL" -p "$HEROKU_API_KEY" "$HEROKU_REGISTRY_URL";

#    docker tag zephyr/ping:release registry.heroku.com/ping/web;
#    docker push registry.heroku.com/ping/web;
fi