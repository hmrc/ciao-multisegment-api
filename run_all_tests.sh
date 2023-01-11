#!/bin/bash

sbt clean coverage scalafixAll scalafmtAll test coverageOff coverageReport
