package at.spin2time.exceptions;

import com.amazonaws.AmazonClientException;

public class S2TPersonalizationException extends AmazonClientException {

    public S2TPersonalizationException(String message){
        super(message);
    }

}
