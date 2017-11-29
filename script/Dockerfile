FROM python:3.6.1
ADD ./resolv.conf  /etc/resolv.conf 
ADD ./python /home/
WORKDIR /home/
RUN pip3 install beautifulsoup4 lxml 
VOLUME ["/out"]
CMD "python" "gfwlist2web.py" "-o" "/out/gfwlistOutput.json" "-a" "/out/alexa.json"

