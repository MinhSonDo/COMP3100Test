# ds-sim server 14-Feb, 2022 @ MQ - client-server
# Server-side simulator started with './ds-server -c ds-config01--wk9.xml -v brief -n'
# Waiting for connection to port 50000 of IP address 127.0.0.1
RCVD HELO
SENT OK
RCVD AUTH minh
# Welcome  minh!
# The system information can be read from 'ds-system.xml'
SENT OK
RCVD REDY
SENT JOBN 32 0 728 1 700 600
RCVD SCHD 0 tiny 0
t:         32 job     0 (waiting) on # 0 of server tiny (booting) SCHEDULED
SENT OK
RCVD REDY
SENT JOBN 54 1 1144 1 400 800
RCVD SCHD 1 small 0
t:         54 job     1 (waiting) on # 0 of server small (booting) SCHEDULED
SENT OK
RCVD REDY
SENT JOBN 55 2 260 2 900 1600
RCVD SCHD 2 medium 0
t:         55 job     2 (waiting) on # 0 of server medium (booting) SCHEDULED
SENT OK
RCVD REDY
t:         72 job     0 on # 0 of server tiny RUNNING
t:         94 job     1 on # 0 of server small RUNNING
SENT JOBN 108 3 151 2 500 3300
RCVD SCHD 3 medium 0
t:        108 job     3 (waiting) on # 0 of server medium (booting) SCHEDULED
SENT OK
RCVD REDY
t:        115 job     2 on # 0 of server medium RUNNING
t:        115 job     3 on # 0 of server medium RUNNING
t:        252 job     3 on # 0 of server medium COMPLETED
SENT JCPL 252 3 medium 0
RCVD REDY
SENT JOBN 287 4 3936 4 1600 4600
RCVD SCHD 4 medium 0
t:        287 job     4 (waiting) on # 0 of server medium (active) SCHEDULED
SENT OK
RCVD REDY
t:        292 job     2 on # 0 of server medium COMPLETED
t:        292 job     4 on # 0 of server medium RUNNING
SENT JCPL 292 2 medium 0
RCVD REDY
t:       1902 job     0 on # 0 of server tiny COMPLETED
SENT JCPL 1902 0 tiny 0
RCVD REDY
t:       2287 job     1 on # 0 of server small COMPLETED
SENT JCPL 2287 1 small 0
RCVD REDY
t:       2833 job     4 on # 0 of server medium COMPLETED
SENT JCPL 2833 4 medium 0
RCVD REDY
SENT NONE
RCVD QUIT
SENT QUIT
# -------------------------------------------------------------------------------------
# 1 tiny servers used with a utilisation of 100.00 at the cost of $0.20
# 1 small servers used with a utilisation of 100.00 at the cost of $0.24
# 1 medium servers used with a utilisation of 100.00 at the cost of $0.60
# ==================================== [ Summary ] ====================================
# actual simulation end time: 2833, #jobs: 5 (failed 0 times)
# total #servers used: 3, avg util: 100.00% (ef. usage: 100.00%), total cost: $1.05
# avg waiting time: 30, avg exec time: 1375, avg turnaround time: 1405