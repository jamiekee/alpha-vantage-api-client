import org.springframework.web.client.RestTemplate;

class Request {

  static String sendRequest(String pathVariables) {
    return new RestTemplate().getForEntity(
        ALPHA_VANTAGE_URL + pathVariables,
        String.class
    ).getBody();
  }

  private final static String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query?";

}
