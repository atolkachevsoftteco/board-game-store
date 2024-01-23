#!/bin/sh
set -eu

gradle build -p ./repo
cp ./repo/build/libs/-*.jar ./build-output