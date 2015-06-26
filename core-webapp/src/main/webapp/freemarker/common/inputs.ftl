<#ftl strip_whitespace=true>
<#import "/spring.ftl" as spring />
<#-- This file contains form-related macros for use in the other Freemarker template files.
     The generated HTML is intended for use with Twitter Bootstrap based forms. -->


<#--
 * radioButtons
 *
 * @param path the name of the field to bind to
 * @param options a map (value=label) of all the available options
 * @param classes any additional classes for the surrounding label element (such as 'inline')
 * @param messageKey key to lookup in resource bundle, defaults to path
-->
<#macro radioButtons path options classes="" checkId=false messageKey=path>
    <@spring.bind path/>
    <#assign error><#if spring.status.errorMessages?has_content>error</#if></#assign>
<div class="control-group ${error}">
<#-- use id for selected check if property is an entity -->
    <#if checkId && spring.status.value??><#assign stringStatusValue=spring.status.value.id?string in spring></#if>
    <label class="control-label">
        <@spring.message messageKey/>
        <#if error?has_content><span class="help-block">${spring.status.errorMessages?first}</span></#if>
    </label>
    <div class="controls">
        <#list options?keys as value>
            <#assign id="${spring.status.expression?replace('[','')?replace(']','')}${value_index}">
            <label class="radio ${classes}">
                <input type="radio" id="${id}" name="${spring.status.expression}" value="${value?html}"<#if spring.stringStatusValue == value?string> checked</#if> >
            ${options[value]?html}
            </label>
        </#list>
    </div>
</div>
</#macro>

<#--
 * textInput
 *
 * @param path the name of the field to bind to
 * @param attributes any additional attributes for the element (such as class or CSS styles or size)
 * @param messageKey key to lookup in resource bundle, defaults to path
 * @param type input type: defaults to "text"
-->
<#macro textInput path attributes="" messageKey=path type="text">
    <@spring.bind path/>
    <#assign error><#if spring.status.errorMessages?has_content>error</#if></#assign>
<div class="control-group ${error}">
    <#assign id="${spring.status.expression?replace('[','')?replace(']','')}">
    <label class="control-label" for="${id}"><@spring.message messageKey/></label>
    <div class="controls">
        <input type="${type}" id="${id}" name="${spring.status.expression}" value="${spring.stringStatusValue}" ${attributes}>
        <#if error?has_content><span class="help-inline">${spring.status.errorMessages?first}</span></#if>
        <#nested>
    </div>
</div>
</#macro>

<#macro textInputWithMessage path attributes="" message="" type="text">
    <@spring.bind path/>
    <#assign error><#if spring.status.errorMessages?has_content>error</#if></#assign>
<div class="form-group ${error}">
    <#assign id="${spring.status.expression?replace('[','')?replace(']','')}">
    <label class="control-label col-sm-2" for="${id}">${message}</label>
    <div class="controls col-sm-4">
        <input type="${type}" id="${id}" name="${spring.status.expression}" value="${spring.stringStatusValue}" ${attributes}>
    </div>
</div>
</#macro>

<#--
 * textArea
 *
 * @param path the name of the field to bind to
 * @param attributes any additional attributes for the element (such as class or CSS styles or size)
 * @param messageKey key to lookup in resource bundle, defaults to path
-->
<#macro textArea path attributes="" messageKey=path>
    <@spring.bind path/>
    <#assign error><#if spring.status.errorMessages?has_content>error</#if></#assign>
<div class="control-group ${error}">
    <#assign id="${spring.status.expression?replace('[','')?replace(']','')}">
    <label class="control-label" for="${id}"><@spring.message messageKey/></label>
    <div class="controls">
        <textarea id="${id}" name="${spring.status.expression}" ${attributes}>${spring.stringStatusValue}</textArea>
        <#if error?has_content><span class="help-inline">${spring.status.errorMessages?first}</span></#if>
    </div>
</div>
</#macro>

<#--
 * singleSelect
 *
 * Show a selectbox (dropdown) input element allowing a single value to be chosen
 * from a list of options.
 *
 * @param path the name of the field to bind to
 * @param options a map (value=label) or list (value) of all the available options
 * @param attributes any additional attributes for the select element (such as class or CSS styles or size)
 * @param checkId does path refer to an entity and should the selected check be done based on that entity's id (defaults to false)
 * @param emptyOption should an empty first option be inserted (defaults to false); an empty option is also created when an empy
                      string makes up the value of the option
-->
<#macro singleSelect path options messageKey=path attributes="" checkId=false emptyOption=false>
    <@spring.bind path/>
<#-- use id for selected check if property is an entity -->
    <#if checkId && spring.status.value??><#assign stringStatusValue=spring.status.value.id?string in spring></#if>
    <#assign error><#if spring.status.errorMessages?size gt 0>error</#if></#assign>
<div class="control-group ${error}">
    <#assign id="${spring.status.expression?replace('[','')?replace(']','')}">
    <label class="control-label" for="${id}"><@spring.message messageKey/></label>
    <div class="controls">
        <select id="${id}" name="${spring.status.expression}" ${attributes}>
            <#if emptyOption>
                <option value="">-- <@spring.message "select.choose"/> --</option>
            </#if>
            <#if options?is_hash>
                <#list options?keys as value>
                    <#assign label><#if value = "">-- <@spring.message "select.choose"/> --<#else>${options[value]?html}</#if></#assign>
                    <option value="${value?html}" <@spring.checkSelected value/>>${label}</option>
                </#list>
            <#else>
                <#list options as value>
                    <#assign label><#if value = "">-- <@spring.message "select.choose"/> --<#else>${value?html}</#if></#assign>
                    <option value="${value?html}" <@spring.checkSelected value/>>${label}</option>
                </#list>
            </#if>
        </select>
        <@spring.showErrors ", " "help-inline"/>
    </div>
</div>
</#macro>


<#--
 * submitButton
 *
 * @param value submit input value (default to message with key 'button.save')
 * @param buttonStyle extra classes to add to button (defaults to "save-button")
 * @param controlGroup should the input be wrapped in a Bootstrap .control-group with a .controls? (defaults to true)
-->
<#macro submitButton value=rc.getMessage('button.save') buttonStyle="save-button" controlGroup=true>
    <#if controlGroup>
    <div class="control-group">
    <div class="controls">
    </#if>
    <input type="submit" value="${value}" class="${buttonStyle}">
    <#if controlGroup>
    </div>
    </div>
    </#if>
</#macro>

<#--
 * checkboxes
 *
 * Show checkboxes.
 *
 * @param path the name of the field to bind to
 * @param options a map (value=label) of all the available options
 * @param attributes any additional attributes for the element (such as class or CSS styles or size)
-->
<#macro checkboxes path options attributes="">
    <@spring.bind path/>
    <#list options?keys as value>
        <#assign id="${spring.status.expression?replace('[','')?replace(']','')}${value_index}">
        <#assign isSelected = spring.contains(spring.status.actualValue?default([""]), value)>
    <div class="control-group">
        <label class="control-label" for="${id}">${options[value]?html}</label>
        <div class="controls">
            <input type="checkbox" id="${id}" name="${spring.status.expression}" value="${value?html}"<#if isSelected> checked="checked"</#if> ${attributes}>
        </div>
    </div>
    </#list>
<input type="hidden" name="_${spring.status.expression}" value="on"/>
</#macro>

<#--
 * enumcheckboxes
 *
 * Show checkboxes based on enum values. Labels are resource bundle lookups by "[enumType.class.simpleName].[name]", falling back to enum's toString().
 *
 * @param path the name of the field to bind to
 * @param enum fully qualified type name of the enum
 * @param attributes any additional attributes for the element (such as class or CSS styles or size)
-->
<#macro enumcheckboxes path enum attributes="">
    <@spring.bind path/>
    <#list enums[enum]?values as value>
        <#assign id="${spring.status.expression?replace('[','')?replace(']','')}${value_index}">
        <#assign isSelected = spring.contains(spring.status.actualValue?default([""]), value)>
    <div class="control-group">
        <label class="control-label" for="${id}"><@spring.messageText (value.class.simpleName + '.' + value.name()) value.toString()/></label>
        <div class="controls">
            <input type="checkbox" id="${id}" name="${spring.status.expression}" value="${value.name()?html}"<#if isSelected> checked="checked"</#if> ${attributes}>
        </div>
    </div>
    </#list>
<input type="hidden" name="_${spring.status.expression}" value="on"/>
</#macro>

<#--
 * checkbox
 *
 * Show a single checkbox.
 *
 * @param path the name of the field to bind to
 * @param messageKey resource key to lookup, defaults to path
 * @param attributes any additional attributes for the element (such as class or CSS styles or size)
-->
<#macro checkbox path messageKey=path attributes="">
    <@spring.bind path />
    <#assign id="${spring.status.expression?replace('[','')?replace(']','')}">
    <#assign isSelected = spring.status.value?? && spring.status.value?string=="true">
<input type="hidden" name="_${id}" value="on"/>
<div class="control-group">
    <label class="control-label" for="${id}"><@spring.message messageKey/></label>
    <div class="controls">
        <input type="checkbox" id="${id}" name="${id}"<#if isSelected> checked="checked"</#if> ${attributes}/>
    </div>
</div>
</#macro>
