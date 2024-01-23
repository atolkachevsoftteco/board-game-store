#!/bin/sh
set -eu

gradle build -p ./project-repo
cp ./project-repo/build/libs/-*.jar ./build-output