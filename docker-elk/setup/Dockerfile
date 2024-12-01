ARG ELASTIC_VERSION

# https://www.docker.elastic.co/
FROM docker.io/library/elasticsearch:${ELASTIC_VERSION:-8.16.0}

ENTRYPOINT ["/entrypoint.sh"]
