#!/bin/bash

if test -z $1; then
    exit 1;
fi

CLASS_NAME=`basename $1`;
CLASS_NAME=${CLASS_NAME%.java}

cat > $1 <<EOF
/*
 * $1
 * Automatically generated on `date` *do not edit *
 */
package cis.profiler.web;
public class  $CLASS_NAME extends ProfilerWebService {
}
EOF
