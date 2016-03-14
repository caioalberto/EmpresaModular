package br.com.caioribeiro.empresa.stringbuilder;

import org.apache.commons.lang3.builder.ToStringStyle;

public final class MyStyle extends ToStringStyle {

    private static final long serialVersionUID = 1L;

    MyStyle() {
        super();
        this.setFieldNameValueSeparator(null);
        this.setUseClassName(false);
        this.setUseIdentityHashCode(false);
        this.setUseFieldNames(true);
        this.setFieldSeparator("\n");
        this.setContentStart("");
        this.setContentEnd(" ");
    }
    
    public static final ToStringStyle MY_STYLE = new MyStyle();

}
