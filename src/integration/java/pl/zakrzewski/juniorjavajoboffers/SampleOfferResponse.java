package pl.zakrzewski.juniorjavajoboffers;

public interface SampleOfferResponse {

    default String bodyWithZeroOffersJson() {
        return """
                 {
                   "postings": [
                   ]
                 }
                """.trim();
    }

    default String bodyWithOneOfferJson() {
        return """
                 {
                   "postings": [
                     {
                       "name": "Some startup",
                       "location": {
                         "fullyRemote": true
                       },
                       "title": "Junior Java Developer ",
                       "url": "junior-java-developer-startup",
                       "salary": {
                         "from": 7000,
                         "to": 9000
                       }
                     }
                  ]
                }
                """;
    }

    default String bodyWithThreeOffersJson() {
        return """
                {
                   "postings": [
                     {
                       "name": "Some startup",
                       "location": {
                         "fullyRemote": true
                       },
                       "title": "Junior Java Developer ",
                       "url": "junior-java-developer-startup",
                       "salary": {
                         "from": 7000,
                         "to": 9000
                       }
                     },
                     {
                       "name": "I love programming",
                       "location": {
                         "fullyRemote": true
                       },
                       "title": "Junior Software Engineer",
                       "url": "java-software-engineer-i-love-programming",
                       "salary": {
                         "from": 5000,
                         "to": 9000
                       }
                     },
                     {
                       "name": "Java world",
                       "location": {
                         "fullyRemote": false
                       },
                       "title": "Junior Java Developer",
                       "url": "junior-java-developer-java-world",
                       "salary": {
                         "from": 6000,
                         "to": 8000
                       }
                     }
                   ]
                 }
                                                                                 """.trim();
    }
}
