
package com.cisco.cstg.autotools.domain.appdb;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class IdentifiableAppEntity implements Serializable {

    private static final long serialVersionUID = 4681596106749607674L;

}
