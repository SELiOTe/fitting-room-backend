#!/bin/bash

## Redis create script
## Version: Redis 6.2.0
## Usage: `bash create.sh`

echo "Add user"
echo "ACL SETUSER dcp6pX0 on >oAnNpFg1IidswJ5c5ZZHoEYLqD93Ufvq ~fr:* +@all" | redis-cli

echo "Save config"
echo "ACL SAVE" | redis-cli
