#!/bin/bash

## Server environment create script
## Usage: root user execute `bash create.sh ${PATH}`
##        ${PATH} is sys.resource-path defined in application.properties
## If you want undo this, please manual `rm -rf ${PATH}`

path="${1}"
hex='0123456789abcdef'
log_path="log"
image_path="image"

if [ -z "${path}" ] || [ ! "${path:0:1}" = "/" ]; then
  echo "PATH param could not be empty and must absolute path"
  exit 1
fi

echo "Create directory: ${path}"
mkdir -p "${path}"

log_path="${path}/${log_path}"
echo "Create directory: ${log_path}"
mkdir "${log_path}"

image_path="${path}/${image_path}"
echo "Create directory: ${image_path}"
mkdir "${image_path}"

for ((i = 0; i < 16; i++)); do
  dir="${image_path}/${hex:i:1}"
  echo "Create directory: ${dir}"
  mkdir "${dir}"
  for ((j = 0; j < 16; j++)); do
    dir="${image_path}/${hex:i:1}/${hex:j:1}"
    echo "Create directory: ${dir}"
    mkdir "${dir}"
  done
done

echo "Change owner: ${path}"
if grep "tomcat9" /etc/passwd > /dev/null 2>&1
then
  chown -R tomcat9:tomcat9 "${path}"
else
  chown -R tomcat:tomcat "${path}"
fi

echo "Copy resources"
current_path=$(dirname "${0}")
cp "${current_path}/avatar.png" "${image_path}/0/0/0000000000000000000000000000000000000000"
