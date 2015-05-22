#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: GC-Mac
# @Date:   2015-05-22 17:04:43
# @Last Modified by:   GC-Mac
# @Last Modified time: 2015-05-22 23:31:16

MARGIN = 20
RESULT = ("","WHITE","BLACK","DRAW")
STEP = 5
PLAYER = RESULT[2]
# Captured, Chain, Connectivity, Safe, Centrailize
INIT_COE = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]



 
from subprocess import Popen, PIPE
import re
import sys
import operator

COMPILE = "javac aiproj/squatter/*.java dongtaoy/squatter/*.java"

INSTRUCT = "java aiproj/squatter/Referee 6 dongtaoy.squatter.Dongtaoy dongtaoy.squatter.Dongtaoy %s %s"

def main():
	Popen(COMPILE, shell=True)
	p1_coe = list(INIT_COE)
	p2_coe = list(INIT_COE)
	while True:
		for factor in range(len(INIT_COE)):
			dic1 = {}
			for i in range(0, MARGIN + 1, STEP):
				dic2 = {"WHITE": 0, "BLACK": 0, "DRAW": 0}
				p1_coe[factor] = i
				for j in range(0, MARGIN + 1, STEP):
					p2_coe[factor] = j
					dic2[get_result(p1_coe, p2_coe)] += 1;
					print p1_coe
					print p2_coe
					print
				dic1[i] = dic2["WHITE"] * 3 + dic2["DRAW"] - dic2["BLACK"]
			print dic1
			print
			best = sorted(dic1.items(), key=operator.itemgetter(1))[-1][0]
			p1_coe[factor] = best
			p2_coe[factor] = best
		print "---------------------"
		print p1_coe
		print "---------------------"

	# # for i in range(len(INIT_COE)):
	# for j in range(INIT_COE[0], MARGIN, STEP):
	# 	print "OUTTER: " + str(j)
	# 	for k in range(INIT_COE[0], MARGIN, STEP):
	# 		print "INNER: " + str(k)
	# 		p2_coe[0] += STEP
	# 		get_result(p1_coe, p2_coe)
	# 	p1_coe[0] += STEP
	# 	p2_coe[0] = INIT_COE[0]
		# p2_coe[i] = p1_coe[i]

def get_result(p1_coe, p2_coe):
	p1_arg = reduce(lambda a,b:str(a) + ' ' + str(b),p1_coe)
	p2_arg = reduce(lambda a,b:str(a) + ' ' + str(b),p2_coe)
	init_arg = reduce(lambda a,b:str(a) + ' ' + str(b),INIT_COE)
	process = Popen(INSTRUCT % (p1_arg,p2_arg) , stdout=PIPE, shell=True)
	stdout, stderr = process.communicate()
	print stdout
	return get_winner(stdout)
	 

def get_winner(stdout):
	player_reg = "Player one \(White\) indicate winner as: (\d+)"
	opponent_reg = "Player two \(Black\) indicate winner as: (\d+)"

	try:
		player = (int)(re.findall(player_reg, stdout)[0]);
		opponent = (int)(re.findall(opponent_reg, stdout)[0]);

		#Check!
		if player != opponent:
			raise Exception("Different winner!")
		return  RESULT[player]
	except e:
		print e 
		sys.exit(1)


















if __name__ == "__main__":
	main()



