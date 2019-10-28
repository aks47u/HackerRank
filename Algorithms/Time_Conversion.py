#!/bin/python3

import os
import sys

#
# Complete the timeConversion function below.
#
def timeConversion(s):
    p=s.split(':')
    if 'AM' in s:
        a=s
        if p[0]=='12':
            p[0]="00"
            a=':'.join(p)
        print(a.replace('AM',''))
        
            
            
    elif 'PM' in s:
        if p[0]=='12':
            p[0]='12'
        else:
            p[0]=str(int(p[0])+12)

        a=':'.join(p)
        print(a.replace('PM',''))
        
        
if __name__=='__main__':
    s = input()
    result = timeConversion(s)
