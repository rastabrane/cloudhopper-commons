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

// third party imports
import com.cloudhopper.commons.xbean.ConversionException;
import com.cloudhopper.commons.xbean.ConversionOverflowException;
import com.cloudhopper.commons.xbean.PropertyConverter;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

// my imports

public class BytePropertyConverterTest {
    private static final Logger logger = Logger.getLogger(BytePropertyConverterTest.class);

    private PropertyConverter[] pcs = new PropertyConverter[] {
        new BytePropertyConverter(),
        new BytePrimitivePropertyConverter()
    };
    
    @Test
    public void validConversions() throws Exception {
        for (PropertyConverter pc : pcs) {
            Assert.assertEquals(Byte.valueOf((byte)0), pc.convert("0"));
            // min and max value of a byte
            Assert.assertEquals(Byte.valueOf((byte)-128), pc.convert("-128"));
            Assert.assertEquals(Byte.valueOf((byte)127), pc.convert("127"));
            // hex versions
            Assert.assertEquals(Byte.valueOf((byte)0), pc.convert("0x0"));
            Assert.assertEquals(Byte.valueOf((byte)0), pc.convert("0X0"));
            Assert.assertEquals(Byte.valueOf((byte)127), pc.convert("0x7F"));
            Assert.assertEquals(Byte.valueOf((byte)0xFF), pc.convert("0xFF"));
        }
    }
    
    @Test
    public void overflow() throws Exception {
        for (PropertyConverter pc : pcs) {
            try {
                pc.convert("0x100");
            } catch (ConversionOverflowException e) {
                // valid
            }
        }
    }
    
    @Test
    public void negativeInHexNotAllowed() throws Exception {
        for (PropertyConverter pc : pcs) {
            try {
                pc.convert("-0x01");
            } catch (ConversionException e) {
                // valid
            }
        }
    }
}
