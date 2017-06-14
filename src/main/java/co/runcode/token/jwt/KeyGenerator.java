package co.runcode.token.jwt;

import java.security.Key;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public interface KeyGenerator {

    Key generateKey();
}
