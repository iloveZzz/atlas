/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.metadata.repository;

import org.apache.hadoop.metadata.IReferenceableInstance;
import org.apache.hadoop.metadata.ITypedReferenceableInstance;
import org.apache.hadoop.metadata.MetadataException;
import org.apache.hadoop.metadata.service.Service;
import org.apache.hadoop.metadata.storage.RepositoryException;
import org.codehaus.jettison.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An interface for persisting metadata into a blueprints enabled graph db.
 */
public interface MetadataRepository extends Service {

    String createEntity(IReferenceableInstance entity,
                        String entityType) throws RepositoryException;

    ITypedReferenceableInstance getEntityDefinition(String guid) throws RepositoryException;

    List<String> getEntityList(String entityType) throws RepositoryException;

    /**
     * Assumes the User is familiar with the persistence structure of the Repository.
     * The given query is run uninterpreted against the underlying Graph Store.
     * The results are returned as a List of Rows. each row is a Map of Key,Value pairs.
     *
     * @param gremlinQuery query in gremlin dsl format
     * @return List of Maps
     * @throws org.apache.hadoop.metadata.MetadataException
     */
    List<Map<String,String>> searchByGremlin(String gremlinQuery) throws MetadataException;
    
    /**
     * Simple direct graph search and depth traversal.
     * @param searchText is plain text
     * @param prop is the Vertex property to search.
     */
    Map<String, HashMap<String,JSONObject>> textSearch(String searchText, int depth, String prop);
    
    /**
     * Simple graph walker for search interface, which allows following of specific edges only.
     * @param edgesToFollow is a comma-separated-list of edges to follow.
     */
    Map<String, HashMap<String,JSONObject>> relationshipWalk(String guid, int depth, String edgesToFollow);
       
    /**
     * Return a Set of indexed properties in the graph.
     * No parameters.
     */
    Set<String> getGraphIndexedFields();
}
