#!/usr/bin/env bash
cat $1 | sqlcmd -s localhost -u sa -p Admin2018 -o 1433