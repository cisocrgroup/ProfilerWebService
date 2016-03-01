#!/bin/sh

if test -z $1; then
    echo "Missing out file"
    exit 1;
fi
if test -z $2; then
    echo "Missing backend"
    exit 1;
fi

if test -z $3; then
    echo "Missing profiler"
    exit 1;
fi

cat > $1 <<EOF
#
# profiler.ini
# automatically generated on `date` *do not edit*
#
backend = $2
profiler = $3
# DEPRECATED SETTINGS
dbURL = jdbc:mysql://alpha.cis.uni-muenchen.de:3306/ProfilerWebService
username = pws
password = pws1314
default_quota = 100
backend_home = /mounts/Users/student/finkf/uni/profiler/tomcat/postcorrection_backend
EOF
