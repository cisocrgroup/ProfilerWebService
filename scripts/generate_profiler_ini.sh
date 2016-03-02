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
EOF
