#!/bin/bash
set -e
DIR=$(cd "$(dirname $0)" && pwd)
IN_CONTAINER=true
case $1 in
"start")
  source ${DIR}/start.sh
  ;;
"stop")
  source ${DIR}/stop.sh
  ;;
"restart")
  source ${DIR}/restart.sh
  ;;
*)
  exec "$@"
  ;;
esac
