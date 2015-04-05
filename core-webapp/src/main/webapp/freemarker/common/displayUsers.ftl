<#macro displayUsers>
<div class="displayUsers">
    <#assign inc = 1>
    <table class="displayUsers">
      <tr>
          <th>
              user no.
          </th>
          <th>
              user name
          </th>
      </tr>
      <#list tests as test>
      <tr>
          <td>
              ${inc}
          </td>
          <#assign inc = inc +1>
          <td>
              ${test.word}
          </td>
      </tr>
      </#list>
     </table>
</div>
</#macro>