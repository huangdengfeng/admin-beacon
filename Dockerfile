FROM openjdk:17
LABEL maintainer="734839030@qq.com"
ARG MODULE_NAME
WORKDIR /data/${MODULE_NAME}

# for shell to choose environment
ENV IN_CONTAINER=true
# just src content
COPY target/${MODULE_NAME} /data/${MODULE_NAME}/

EXPOSE 8080
ENTRYPOINT ["bin/docker-entrypoint.sh"]
CMD ["start"]