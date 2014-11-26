/*
 * Copyright (C) 2014 Luca Giordano, Lucio Martínez.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package controllers;

/**
 *
 * @author alumno
 */
public abstract class Controller {

    /**
     * Check if a string is empty or only white spaces
     * @param s The string to check
     * @return True if the string is empty or white space, otherwise false.
     */
    static boolean emptyOrWhiteSpace(String s) {
        return (s == null || s.trim().isEmpty());
    }

    /**
     * Check if a string is suitable as a real entity name
     * @param name The string to check
     * @return False if the string is not valid as name, otherwise true.
     */
    protected static boolean validName(String name) {
        final int minL = 3,
                maxL = 200,
                l = (name != null) ? name.length() : 0;

        return (!emptyOrWhiteSpace(name) && l >= minL && l <= maxL);
    }

    /**
     * Check if a string is suitable as a username for the authentication system
     * @param username The username to check
     * @return False if the string is not valid as username, otherwise true.
     */
    protected static boolean validUsername(String username) {
        final int minL = 3,
                maxL = 50,
                l = (username != null) ? username.length() : 0;

        return (!emptyOrWhiteSpace(username) && l >= minL && l <= maxL);
    }

    protected static boolean validPassword(String password) {
        final int minL = 3,
                maxL = 64500,
                l = (password != null) ? password.length() : 0;

        return (!emptyOrWhiteSpace(password) && l >= minL && l <= maxL);
    }

}
