FROM python:3.6.1
ADD ./resolv.conf  /etc/resolv.conf 
ADD ./gfwlist.txt  /home/gfwlist.txt
ADD ./gfwlist2web.py /home/gfwlist2web.py
WORKDIR /home/
VOLUME ["/out"]
CMD "python" "gfwlist2web.py" "-o" "/out/gfwlistOutput.json"
