#!/bin/sh

if test -z $1; then
    exit 1;
fi

cat > $1 <<EOF
#
# profiler.ini
# automatically generated on `date` *do not edit *
#
dbURL = jdbc:mysql://alpha.cis.uni-muenchen.de:3306/ProfilerWebService
username = pws
password = pws1314
default_quota = 100
backend_home = /mounts/Users/student/finkf/uni/profiler/tomcat/postcorrection_backend

EOF
