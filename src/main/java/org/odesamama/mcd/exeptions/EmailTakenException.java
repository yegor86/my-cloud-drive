package org.odesamama.mcd.exeptions;

import org.odesamama.mcd.ErrorMessages;

/**
 * Created by starnakin on 15.10.2015.
 */
public class EmailTakenException extends ClientException{
    public EmailTakenException() {
        super(ErrorMessages.EMAIL_IS_TAKEN);
    }
}
