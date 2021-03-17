#!/bin/bash

## Redis drop script
## Usage: bash drop.sh

echo "Delete data"
echo "FLUSHALL"

echo "Delete user"
echo "ACL DELUSER dcp6pX0" | redis-cli

echo "Save config"
echo "ACL SAVE" | redis-cli