# Ciao multi-segment context API

This service has been created to test APIs with multiple segments in their context.
It is used in the API Platform acceptance tests [here](https://github.com/hmrc/api-platform-acceptance-tests/blob/main/src/test/resources/features/apis/multisegmentContextApi.feature#L2) and [here](https://github.com/hmrc/api-platform-acceptance-tests/blob/main/src/test/resources/features/platform/apiPublisher.feature#L10).
This service can be used to test successful/failed publish of an API which has multiple slashes in its context.

See the table below for examples of multi segmented contexts.

| Context                               | API - Service name                  | Multi-segment <br/>Context? |
|---------------------------------------|-------------------------------------|-----------------------------|
| ciao/hey/welcome                      | ciao-multisegmeent-api              | Yes                         |
| misc/telephony-identity-verification  | telephony-identity-verification-api | Yes                         |
| hello                                 | api-example-microservice            | No                          |


---

### License

This code is open source software licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html)
