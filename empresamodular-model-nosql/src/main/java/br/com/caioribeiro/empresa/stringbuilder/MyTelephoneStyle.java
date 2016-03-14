package br.com.caioribeiro.empresa.stringbuilder;

import org.apache.commons.lang3.builder.ToStringStyle;

public final class MyTelephoneStyle extends ToStringStyle {

    private static final long serialVersionUID = 1L;

    MyTelephoneStyle() {
        super();
        this.setFieldNameValueSeparator(null);
        this.setUseClassName(false);
        this.setUseIdentityHashCode(false);
        this.setUseFieldNames(true);
        this.setContentStart("");
        this.setContentEnd(" ");
    }
    
    public static final ToStringStyle MY_TELEPHONE_STYLE = new MyTelephoneStyle();

}



