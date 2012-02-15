/**
 * Copyright (C) 2011 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.cloudhopper.commons.xbean.convert;

import com.cloudhopper.commons.util.ByteArrayUtil;
import com.cloudhopper.commons.util.HexUtil;
import com.cloudhopper.commons.xbean.ConversionException;
import com.cloudhopper.commons.xbean.PropertyConverter;
import com.cloudhopper.commons.xbean.util.NumberRadixResult;
import com.cloudhopper.commons.xbean.util.NumberRadixUtil;

/**
 * Converts a String to a long.
 * @author joelauer
 */
public class LongPrimitivePropertyConverter implements PropertyConverter {

    @Override
    public Object convert(String value) throws ConversionException {
        NumberRadixResult result = NumberRadixUtil.parseNumberRadix(value);
        try {
            if (result.getRadix() == 16) {
                String hex = NumberRadixUtil.normalizeLeadingHexZeroes(result.getNumber(), 16);
                byte[] bytes = HexUtil.toByteArray(hex);
                return ByteArrayUtil.toLong(bytes);
            } else {
                return Long.parseLong(result.getNumber());
            }
        } catch (NumberFormatException e) {
            throw new ConversionException(e.getMessage());
        }
    }
    
}
