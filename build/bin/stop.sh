#!/bin/bash
set -e
DIR=$(cd "$(dirname $0)" && pwd)
${DIR}/catalina.sh stop
