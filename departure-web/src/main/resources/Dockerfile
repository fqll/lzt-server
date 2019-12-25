FROM registry.afanqie.cn/jdk8
ENV LANG en_US.UTF-8
ADD departure-web-0.0.1-SNAPSHOT.jar app.jar
ADD start.sh /start.sh
RUN chmod a+x /start.sh
ENTRYPOINT ["/start.sh"]

