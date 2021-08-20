import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {IdeasResolver} from "./shared/resolvers/ideas.resolver";
import {IdeasComponent} from "./ideas/ideas.component";
import {CreateIdeaComponent} from "./create-idea/create-idea.component";
import {IdeaFillComponent} from "./idea-fill/idea-fill.component";
import {IdeaResolver} from "./shared/resolvers/idea.resolver";
import {HomeComponent} from "./home/home.component";
import {UpdateIdeaComponent} from "./update-idea/update-idea.component";
import {TagResolver} from "./shared/resolvers/tag.resolver";

const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        data: { animation: 'home' },
    },
    {
        path: 'ideas',
        component: IdeasComponent,
        data: { animation: 'ideas' },
        resolve: {
            ideas: IdeasResolver
        },
    },
    {
        path: 'ideas/id/:id',
        component: IdeaFillComponent,
        data: { animation: 'idea' },
        resolve: {
            idea: IdeaResolver
        }
    },
    {
        path: 'ideas/:id/update',
        component: UpdateIdeaComponent,
        data: { animation: 'idea-update' },
        resolve: {
            idea: IdeaResolver
        }
    },
    {
        path: 'ideas/create',
        component: CreateIdeaComponent,
        data: { animation: 'idea-create' },
        resolve: {
            tags: TagResolver
        }
    },
]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}