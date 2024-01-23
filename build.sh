#!/bin/sh
set -eu

gradle build -p ./repo -x test
cp ./repo/build/libs/-*.jar ./build-output