package com.red.webapp.api.stock;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.config.ListFactoryBean;
import org.springframework.core.GenericCollectionTypeResolver;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 2015-09-05.
 */
public class DowListProvider extends ListFactoryBean
{
    private Class targetListClass;
    private Resource[] locations;
    private boolean sourceListSet;

    @Override
    public void setSourceList(List sourceList)
    {
        super.setSourceList(sourceList);
        sourceListSet = true;
    }

    @Override
    public void setTargetListClass(Class targetListClass)
    {
        super.setTargetListClass(targetListClass);
        this.targetListClass = targetListClass;
    }

    public void setLocation(Resource location)
    {
        this.locations = new Resource[]{location};
    }

    public void setLocations(Resource[] locations)
    {
        this.locations = locations;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List createInstance()
    {
        List result = null;
        if (this.sourceListSet)
        {
            result = super.createInstance();
        }
        if (this.locations != null)
        {
            List resourceList = null;
            if (result == null)
            {
                result = instantiateListClass();
            }

            Class valueType = null;

            for (Resource resource : locations)
            {
                List contents = this.resolveList(resource);

                if (this.targetListClass != null)
                {
                    valueType = GenericCollectionTypeResolver.getCollectionType(this.targetListClass);
                }
                if (valueType != null)
                {
                    TypeConverter converter = getBeanTypeConverter();
                    for (Object elem : contents)
                    {
                        result.add(converter.convertIfNecessary(elem, valueType));
                    }
                } else
                {
                    result.addAll(contents);
                }
            }
        }
        if (result == null)
        {
            throw new IllegalArgumentException("You must set either the sourceList, locations or location parameters");
        }
        return result;
    }

    private List instantiateListClass()
    {
        List result;
        if (this.targetListClass != null)
        {
            result = (List) BeanUtils.instantiateClass(this.targetListClass);
        } else
        {
            result = new ArrayList();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private List resolveList(Resource resource)
    {
        InputStream inStream = null;
        List lines = null;
        try
        {
            inStream = resource.getInputStream();
            lines = IOUtils.readLines(inStream);
        } catch (IOException e)
        {
            throw new IllegalArgumentException("Could not read resource " + resource.getDescription(), e);
        } finally
        {
            IOUtils.closeQuietly(inStream);
        }
        return lines;
    }
}
