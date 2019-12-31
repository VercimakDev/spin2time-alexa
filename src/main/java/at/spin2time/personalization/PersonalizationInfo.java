package at.spin2time.personalization;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PersonalizationInfo {

    /**
     * PrincipleId is filled with userId if there's no personId
     */
    private String principleId;

    /**
     * The personId from the Request
     * Id of linked Person to the speakers Voice-Profile
     * Will be "" if no PersonId exists
     */
    private String personId;

    /**
     * The userId from the Request
     * Id of Amazon-Account that is linked to Alexa device
     */
    private String userId;

    /**
     * Boolean value isPersonalized
     * true - personalized request
     * false - nonpersonalized request
     */
    private boolean isPersonalized;

}
