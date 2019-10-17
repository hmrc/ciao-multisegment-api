#!/bin/bash

sbt clean coverage test coverageOff coverageReport
