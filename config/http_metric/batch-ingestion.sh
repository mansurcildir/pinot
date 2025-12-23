batchConfigMapStr=$(python3 -c "import urllib.parse,json; print(urllib.parse.quote(json.dumps({
  'inputFormat':'csv',
  'input.fs.className':'org.apache.pinot.plugin.filesystem.S3PinotFS',
  'input.fs.prop.endpoint':'http://minio:9000',
  'input.fs.prop.accessKey':'admin',
  'input.fs.prop.secretKey':'admin123',
  'input.fs.prop.pathStyleAccess':'true',
  'input.fs.prop.region':'us-east-1'
})))")

curl -X POST "http://localhost:9500/ingestFromURI?tableNameWithType=http_metric_OFFLINE&batchConfigMapStr=$batchConfigMapStr&sourceURIStr=s3://pinot/http-metric/http-metric.csv"
