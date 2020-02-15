FROM java:8-jre

# Add user and group
RUN groupadd -r jetty
RUN useradd -r -g jetty jetty

ENV JETTY_HOME /usr/local/jetty
ENV PATH $JETTY_HOME/bin:$PATH
RUN mkdir -p "$JETTY_HOME"
WORKDIR $JETTY_HOME

ENV JETTY_VERSION 9.4.14.v20181114
ENV JETTY_TGZ_URL https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-distribution/$JETTY_VERSION/jetty-distribution-$JETTY_VERSION.tar.gz

RUN curl -SL "$JETTY_TGZ_URL" -o jetty.tar.gz
RUN tar -xvf jetty.tar.gz --strip-components=1
RUN sed -i '/jetty-logging/d' etc/jetty.conf
RUN rm -fr demo-base javadoc
RUN rm jetty.tar.gz

ENV JETTY_BASE /var/lib/jetty
RUN mkdir -p "$JETTY_BASE"
RUN chown jetty:jetty "$JETTY_BASE"
WORKDIR $JETTY_BASE

RUN modules="$(grep -- ^--module= "$JETTY_HOME/start.ini" | cut -d= -f2 | paste -d, -s)" \
	&& set -xe \
	&& java -jar "$JETTY_HOME/start.jar" --add-to-startd="$modules" \
	&& chown -R jetty:jetty "$JETTY_BASE" \
	&& rm -rf /tmp/hsperfdata_root

ENV JETTY_RUN /run/jetty
ENV JETTY_STATE $JETTY_RUN/jetty.state
ENV TMPDIR /tmp/jetty
RUN mkdir -p "$JETTY_RUN" "$TMPDIR"
RUN chown -R jetty:jetty "$JETTY_RUN" "$TMPDIR"

RUN java -jar $JETTY_HOME/start.jar --add-to-start=http

COPY target/food-service.war $JETTY_BASE/webapps

EXPOSE 8080
CMD ["jetty.sh", "run"]
