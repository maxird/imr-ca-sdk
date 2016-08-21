#!/bin/bash

function clean_folder()
{
  cd $1
  for folder in $(/bin/ls -1 -d */); do
    cd $folder
    rm -rf bin
    rm -rf obj
    rm -f project.lock.json
    cd ..
  done
  cd ..
}

clean_folder src
clean_folder tests

